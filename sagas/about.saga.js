import { delay } from 'redux-saga';
import { all, call, put, take, takeLatest, takeEvery, select } from 'redux-saga/effects';
import actionTypes from '../actions/actionTypes';
import * as actions from '../actions/about.actions';

function * updateGreetingSaga() {
    yield take(actionTypes.UPDATE);
    yield put(actions.updateGreeting('This is from client'));
}

export { updateGreetingSaga };