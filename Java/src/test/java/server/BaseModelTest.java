package test.java.server;

import main.java.models.BaseModel;

abstract class BaseModelTest<M extends BaseModel> {

    /**
     * Assign data that would cause the model validity check to pass
     *
     * @param   obj     model object
     */
    abstract void assignValidData(M obj);

}
