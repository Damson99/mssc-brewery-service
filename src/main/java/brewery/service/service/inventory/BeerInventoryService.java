package brewery.service.service.inventory;

import java.util.UUID;

public interface BeerInventoryService
{

    Integer getOnhandInventory(UUID beerId);
}
