import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
//import fetch from 'isomorphic-unfetch';
import * as actions from '../../actions/actions';
import * as saga from '../../sagas/saga';
import { ProductApi } from '../../api/product.api';

const api = new ProductApi();
const getProduct = async (id) => { return {}; };

api.getProduct = getProduct;
jest.mock('../../api/product.api');
ProductApi.mockImplementation(() => {
    return { getProduct: () => getProduct };
});


describe('Load Product Worker saga', () => {
    it('Should fetch product by id', () => {
        const params = { payload: { id: 1 } }
        const loadProductWorker = saga.loadProductWorker(params);
        const first = loadProductWorker.next().value;
        expect(first).toEqual(call(api.getProduct));
    });

    /*it('Should put action to loadProductSuccess after successfully fetch product by id', async () => {
        const params = { payload: { id: 1 } }
        const loadProductWorker = saga.loadProductWorker(params);
        const first = await loadProductWorker.next().value;
        //expect(first).toEqual();
        const second = loadProductWorker.next().value;
        expect(second).toEqual(put(actions.loadProductSuccess(null)));
    });*/
});