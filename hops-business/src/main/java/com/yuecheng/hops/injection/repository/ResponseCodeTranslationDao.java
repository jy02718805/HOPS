package com.yuecheng.hops.injection.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.injection.entity.ResponseCodeTranslation;

@Service
public interface ResponseCodeTranslationDao  extends PagingAndSortingRepository<ResponseCodeTranslation, Long>, JpaSpecificationExecutor<ResponseCodeTranslation>{
}
