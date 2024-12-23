package com.gc.zelda_api.service.item;

import com.gc.zelda_api.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ItemService {
    Item getItem(String id);
    Page<Item> getItems(PageRequest pageable);
    Page<Item> getItemsByGame(String gameId ,PageRequest pageable);
}
