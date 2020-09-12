package mssc.beer.service.inventory;


import lombok.RequiredArgsConstructor;
import mssc.beer.service.inventory.model.BeerInventoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient
{
    private final InventoryFailoverFeignClient feignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(UUID beerId)
    {
        return feignClient.getOnhandInventory();
    }
}
