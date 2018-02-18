import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest } from 'redux-saga/effects';
//import fetch from 'isomorphic-unfetch';
import { actionTypes, updateGreeting } from './actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    while (true) {
        yield put(updateGreeting());
        yield call(delay, 3000);
    }
}

export default updateGreetingSaga;