package project.genetic.vo;

public interface ICloneable {

    <E extends ICloneable> E doClone();

}
