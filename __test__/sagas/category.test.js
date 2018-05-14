import * as effects from 'redux-saga/effects';
import * as actions from '../../actions/actions';
import * as saga from '../../sagas/saga';
import categoryApi from '../../api/category.api';

describe('Load Categories Worker saga', () => {
    it('Should get categories from store ', () => {
        const generator = saga.loadCategoriesWorker();
        const result = generator.next();
        expect(result.value).toEqual(effects.select());
        expect(result.done).toBeFalsy();
    });
});