package brewery.service.event;

import brewery.service.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable
{
    static final long serialVersionUID = 4745582967909356213L;
    private BeerDto beerDto;
}
