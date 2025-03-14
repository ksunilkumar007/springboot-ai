package com.springboot.ai.springai.service;

import com.springboot.ai.springai.dto.response.WalletResponse;
import com.springboot.ai.springai.entity.Share;
import com.springboot.ai.springai.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class WalletService implements Supplier<WalletResponse> {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletResponse get() {
        return new WalletResponse((List<Share>) walletRepository.findAll());
    }
}
