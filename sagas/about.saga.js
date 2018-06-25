import * as effects from 'redux-saga/effects';
import actionTypes from '../actions/actionTypes';
import * as actions from '../actions/about.actions';

function * updateGreetingSaga() {
    yield effects.take(actionTypes.UPDATE);
    yield effects.put(actions.updateGreeting('This is from client'));
}

export { updateGreetingSaga };