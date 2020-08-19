package brewery.service.web.controller;

import brewery.service.service.BeerService;
import brewery.service.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
public class BeerController
{
    private final BeerService beerService;


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId)
    {
        return new ResponseEntity<BeerDto>(beerService.findBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveBeer(@RequestBody @Validated BeerDto beerDto)
    {
        return new ResponseEntity<BeerDto>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<?> updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto)
    {
        return new ResponseEntity<BeerDto>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}
