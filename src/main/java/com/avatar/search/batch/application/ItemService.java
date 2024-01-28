package com.avatar.search.batch.application;

import com.avatar.search.batch.dto.BatchItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ItemService {

    public void storeItem(BatchItemDTO itemDTO) {
        log.info("----- store item "+ itemDTO.hotel());
    }
}
