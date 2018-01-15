package project.genetic.vo.list;

import project.genetic.vo.ICloneable;

import java.util.List;

public interface ICloneableList<E extends ICloneable> extends List<E>, ICloneable {
}
