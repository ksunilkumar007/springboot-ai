package com.springboot.ai.springai.repository;

import com.springboot.ai.springai.entity.Share;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Share,Long> {
}
