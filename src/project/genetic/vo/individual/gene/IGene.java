package project.genetic.vo.individual.gene;

import project.genetic.vo.ICloneable;

public interface IGene extends ICloneable {

    double value();
    double value(IGene gene);
}
