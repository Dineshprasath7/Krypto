package com.krypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krypto.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin>  getCoinList(int page) throws Exception;

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws JsonProcessingException;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String Keyword);

    String getTop50CoinsByMarketCapRank();

    String GetTradingCoins();

}
