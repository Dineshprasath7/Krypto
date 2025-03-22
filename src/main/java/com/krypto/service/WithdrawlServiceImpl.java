package com.krypto.service;

import com.krypto.domain.WithdrawlStatus;
import com.krypto.model.User;
import com.krypto.model.Withdrawl;
import com.krypto.repository.WithdrawlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawlServiceImpl implements WithdrawlService{
    @Autowired
    private WithdrawlRepository WithdrawlRepository;


    @Override
    public Withdrawl requestWithdrawl(Long amount, User user) {
        Withdrawl Withdrawl=new Withdrawl();
        Withdrawl.setAmount(amount);
        Withdrawl.setStatus(WithdrawlStatus.PENDING);
        Withdrawl.setDate(LocalDateTime.now());
        Withdrawl.setUser(user);
        return WithdrawlRepository.save(Withdrawl);
    }

    @Override
    public Withdrawl procedWithdrawl(Long WithdrawlId,boolean accept) throws Exception {
        Optional<Withdrawl> WithdrawlOptional=WithdrawlRepository.findById(WithdrawlId);

        if(WithdrawlOptional.isEmpty()){
            throw new Exception("Withdrawl id is wrong...");
        }

        Withdrawl Withdrawl=WithdrawlOptional.get();


        Withdrawl.setDate(LocalDateTime.now());

        if(accept){
            Withdrawl.setStatus(WithdrawlStatus.SUCCESS);
        }
        else{
            Withdrawl.setStatus(WithdrawlStatus.DECLINE);
        }

        return WithdrawlRepository.save(Withdrawl);
    }

    @Override
    public List<Withdrawl> getUsersWithdrawlHistory(User user) {
        return WithdrawlRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawl> getAllWithdrawlRequest() {
        return WithdrawlRepository.findAll();
    }

}
