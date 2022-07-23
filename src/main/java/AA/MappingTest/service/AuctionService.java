package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.Auction;
import AA.MappingTest.domain.AuctionHistory;
import AA.MappingTest.domain.Users;
import AA.MappingTest.domain.enums.SaleType;
import AA.MappingTest.exception.LessBidException;
import AA.MappingTest.exception.NoAuctionTypeException;
import AA.MappingTest.exception.CannotBidMyArtException;
import AA.MappingTest.repository.AuctionHistoryRepository;
import AA.MappingTest.repository.AuctionRepository;
import AA.MappingTest.service.DTO.AuctionBidDTO;
import AA.MappingTest.service.DTO.AuctionHighestUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;

    // 1. 경매 등록
    @Transactional
    public Auction registerAuction(Art art, LocalDateTime start, LocalDateTime end){
        if(art.getSaleType() == SaleType.GENERAL){
            throw new NoAuctionTypeException("등록할 때 경매로 등록하지 않았으므로 경매에 등록할 수 없습니다");
        }
        else {
            Auction saveAuction = Auction.createAuction(art.getInitPrice(), start, end, art);
            log.info("\n등록할 경매 = {}", saveAuction);

            auctionRepository.save(saveAuction);
            return saveAuction;
        }
    }

    // 2. [auction_id]에 해당하는 경매 끝났는지 여부
    public boolean isAuctionFinished(Long id, LocalDateTime currentTime){
        Auction findAuction = auctionRepository.findById(id).orElseThrow();
        boolean flag = currentTime.isAfter(findAuction.getEndDate());
        log.info("\n경매 시작 시간 = {} / 경매 종료 시간 = {} / 종료한지? = {}", findAuction.getStartDate(), findAuction.getEndDate(), flag);
        
        return flag;
    }
    
    // 3. [auction_id]에 해당하는 경매가 시작했는지 여부
    public boolean isAuctionStarted(Long id, LocalDateTime currentTime){
        Auction findAuction = auctionRepository.findById(id).orElseThrow();
        boolean flag = currentTime.isAfter(findAuction.getStartDate());
        log.info("\n경매 시작 시간 = {} / 경매 종료 시간 = {} / 시작한지? = {}", findAuction.getStartDate(), findAuction.getEndDate(), flag);
        
        return flag;
    }

    // 4. 현재 경매 최고가 User의 정보 & Bid 금액
    public AuctionHighestUserDTO getInfo(Long id){
        AuctionHighestUserDTO highestUserByAuctionId = auctionRepository.findHighestUserByAuctionId(id);
        log.info("\n현재 경매 최고가 비드 User 정보 = {}", highestUserByAuctionId);

        return highestUserByAuctionId;
    }
    
    // 5. 실시간 경매 진행
    public void executeBid(Long id, AuctionBidDTO auctionBid){
        log.info("\n새로운 비드 정보 = {}", auctionBid);

        Auction findAuction = auctionRepository.findById(id).orElseThrow();
        log.info("\n비드 대상 경매 정보 = {}", findAuction);

        if(Objects.equals(auctionBid.getUser().getId(), findAuction.getArt().getUser().getId())){
            throw new CannotBidMyArtException("본인 작품에는 경매 비드를 할 수 없습니다");
        }
        else {
            if(auctionBid.getBidPrice() < findAuction.getBidPrice()){
                throw new LessBidException("현재 경매가보다 높게 비드해주세요");
            }
            else {
                findAuction.applyNewBid(auctionBid.getUser(), auctionBid.getBidPrice());
                log.info("\n경매에 대한 update bid 정보 = {}", findAuction);

                // AuctionHistory에 추가
                AuctionHistory auctionHistory = AuctionHistory.createAuctionHistory(
                        findAuction,
                        findAuction.getArt(),
                        auctionBid.getUser(),
                        auctionBid.getBidPrice()
                );
                auctionHistoryRepository.save(auctionHistory);

                log.info("\n추가된 AuctionHistory = {}", auctionHistory);
            }
        }
    }

    // 6. [auction_id]에 해당하는 경매의 작품 조회
    public Art getArtFromAuctionId(Long id){
        Art findArtByAuctionId = auctionRepository.findArtByAuctionId(id).getArt();
        log.info("\n{}에 해당하는 작품 = {}", id, findArtByAuctionId);
        return findArtByAuctionId;
    }
}
