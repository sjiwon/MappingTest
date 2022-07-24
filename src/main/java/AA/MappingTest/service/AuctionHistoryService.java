package AA.MappingTest.service;

import AA.MappingTest.domain.AuctionHistory;
import AA.MappingTest.repository.AuctionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionHistoryService {

    private final AuctionHistoryRepository auctionHistoryRepository;

    // [user_id]의 경매 참여 내역 조회
    public List<AuctionHistory> getAuctionListFromUserId(Long id){
        return auctionHistoryRepository.findAuctionHistoriesByUserId(id);
    }

    // [art_id]의 경매 내역 조회
    public List<AuctionHistory> getAuctionListFromArtId(Long id){
        return auctionHistoryRepository.findAuctionHistoriesByArtId(id);
    }

    // [auction_id]의 bid 내역 조회
    public List<AuctionHistory> getAuctionListFromAuctionId(Long id){
        return auctionHistoryRepository.findAuctionHistoriesByAuctionId(id);
    }
}
