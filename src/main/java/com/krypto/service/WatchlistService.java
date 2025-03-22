package com.krypto.service;
import com.krypto.model.User;
import com.krypto.model.Watchlist;
import com.krypto.model.Coin;
public interface WatchlistService {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchList(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addItemToWatchlist(Coin coin,User user) throws Exception;
}
