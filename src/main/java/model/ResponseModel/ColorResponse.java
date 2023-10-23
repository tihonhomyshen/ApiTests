package model.ResponseModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponse {
    public ColorDataResponse data;
    public SupportResponse support;
}
