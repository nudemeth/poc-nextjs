import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import fetch from 'isomorphic-unfetch';
import * as actions from '../../actions/actions';
import * as saga from '../../sagas/saga';

describe('Load Product Worker saga', () => {
    it('Should fetch product by id', () => {
        const params = { payload: { id: 1 } }
        const loadProductWorker = saga.loadProductWorker(params);
        const first = loadProductWorker.next().value;
        expect(first).toEqual(call(fetch, 'http://localhost:5000/product/' + params.payload.id));
        /*const second = loadProductWorker.next().value;
        console.log(second);
        expect(second).toEqual(put(actions.loadProductSuccess(null)));*/
    });
});