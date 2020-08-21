package brewery.service.service;

import brewery.service.domain.Beer;
import brewery.service.repository.BeerRepository;
import brewery.service.web.controller.NotFoundException;
import brewery.service.web.mapper.BeerMapper;
import brewery.service.web.model.BeerDto;
import brewery.service.web.model.BeerPagedList;
import brewery.service.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService
{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand)
    {
        if(showInventoryOnHand)
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        else
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand)
    {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle))
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle))
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle))
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        else
            beerPage = beerRepository.findAll(pageRequest);

        if(showInventoryOnHand)
            beerPagedList = new BeerPagedList(beerPage.getContent().stream().map(beerMapper::beerToBeerDtoWithInventory)
            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        else
            beerPagedList = new BeerPagedList(beerPage.getContent().stream().map(beerMapper::beerToBeerDto)
            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        return beerPagedList;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto)
    {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto)
    {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    @Cacheable(cacheNames = "beerUpcCache")
    public BeerDto getByUpc(String upc)
    {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
