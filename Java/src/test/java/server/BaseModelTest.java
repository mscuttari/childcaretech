package test.java.server;

import main.java.models.BaseModel;

abstract class BaseModelTest<M extends BaseModel> {

    /**
     * Check the equality between two objects of the same class
     *
     * @param   x   first object
     * @param   y   second model
     */
    abstract void assertModelsEquals(M x, M y);


    /**
     * Assign data that would cause the model validity check to pass
     *
     * @param   obj     model object
     */
    abstract void assignValidData(M obj);

}
