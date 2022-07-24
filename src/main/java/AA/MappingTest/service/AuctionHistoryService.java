package AA.MappingTest.service;

import AA.MappingTest.domain.AuctionHistory;
import AA.MappingTest.repository.AuctionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionHistoryService {

    private final AuctionHistoryRepository auctionHistoryRepository;

    // [user_id]의 경매 참여 내역 조회
    public List<AuctionHistory> getAuctionListFromUserId(Long userId){
        return auctionHistoryRepository.findAuctionHistoriesByUserId(userId);
    }

    // [art_id]의 경매 내역 조회
    public List<AuctionHistory> getAuctionListFromArtId(Long artId){
        return auctionHistoryRepository.findAuctionHistoriesByArtId(artId);
    }

    // [auction_id]의 bid 내역 조회
    public List<AuctionHistory> getAuctionListFromAuctionId(Long auctionId){
        return auctionHistoryRepository.findAuctionHistoriesByAuctionId(auctionId);
    }
}
