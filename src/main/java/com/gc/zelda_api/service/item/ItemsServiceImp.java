package com.gc.zelda_api.service.item;

import com.gc.zelda_api.model.Item;
import com.gc.zelda_api.repository.ItemRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ItemsServiceImp implements ItemService {
    private final ItemRepository itemRepository;

    public ItemsServiceImp(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getItem(String id) {
        return itemRepository.findById(id);
    }

    @Override
    public Page<Item> getItems(PageRequest pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> getItemsByGame(String gameId, PageRequest pageable) {
        return itemRepository.findAllByGamesContaining(gameId, pageable);
    }
}
