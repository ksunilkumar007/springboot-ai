package com.springboot.ai.springai.dto.response;

import com.springboot.ai.springai.entity.Share;

import java.util.List;

public record WalletResponse(List<Share> shares) {
}
