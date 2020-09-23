package mssc.beer.web.mapper;


import mssc.beer.domain.Beer;
import mssc.beer.service.inventory.BeerInventoryService;
import mssc.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper
{
    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService)
    {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper)
    {
        this.beerMapper = beerMapper;
    }


    @Override
    public Beer beerDtoToBeer(BeerDto beerDto)
    {
        return beerMapper.beerDtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer)
    {
        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer)
    {
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        beerDto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return beerDto;
    }
}
