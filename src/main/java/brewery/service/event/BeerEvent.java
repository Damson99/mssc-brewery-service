package brewery.service.event;

import brewery.service.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable
{
    static final long serialVersionUID = 4745582967909356213L;
    private final BeerDto beerDto;
}
