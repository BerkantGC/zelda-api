package com.gc.zelda_api.controller;

import com.gc.zelda_api.model.Item;
import com.gc.zelda_api.service.item.ItemsServiceImp;
import com.gc.zelda_api.util.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemsServiceImp itemsServiceImp;
    private final static String DEFAULT_PAGE = "1";
    private final static String DEFAULT_SIZE = "10";

    public ItemController(ItemsServiceImp itemsServiceImp) {
        this.itemsServiceImp = itemsServiceImp;
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getAllItems(
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_SIZE) Integer limit
    ) {
        PageRequest pageRequest = PageRequest.of(page-1, limit);
        return ResponseEntity.ok(
                PageUtils.getJSONByPage(itemsServiceImp.getItems(pageRequest), "items")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        return ResponseEntity.ok(itemsServiceImp.getItem(id));
    }

    @GetMapping("/{game_id}")
    public ResponseEntity<HashMap<String, Object>> getItemByGameId(
            @PathVariable String game_id,
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_SIZE) Integer limit
    ) {
        PageRequest pageRequest = PageRequest.of(page-1, limit);

        return ResponseEntity.ok(
                PageUtils.getJSONByPage(
                        itemsServiceImp.getItemsByGame(game_id, pageRequest),
                        "items"
                )
        );
    }
}
