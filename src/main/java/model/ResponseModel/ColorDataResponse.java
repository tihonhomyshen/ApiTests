package model.ResponseModel;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDataResponse {
    public Integer id;
    public String name;
    public Integer year;
    public String color;
    @JsonProperty("pantone_value")
    public String pantoneValue;
}
