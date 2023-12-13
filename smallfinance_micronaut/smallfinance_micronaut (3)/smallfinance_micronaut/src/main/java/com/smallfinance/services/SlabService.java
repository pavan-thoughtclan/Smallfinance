package com.smallfinance.services;

import com.smallfinance.dtos.inputs.SlabInput;
import com.smallfinance.dtos.outputs.SlabOutput;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface SlabService {
    SlabOutput addSlab(SlabInput slabInputDto);
    List<SlabOutput> getAll();
}
