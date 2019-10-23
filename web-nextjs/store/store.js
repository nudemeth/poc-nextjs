import { createStore, applyMiddleware } from 'redux'
import createSagaMiddleware, { END } from 'redux-saga'
import rootReducer from '../reducers/root.reducer'
import rootSaga from '../sagas/root.saga'

const sagaMiddleware = createSagaMiddleware()

const bindMiddleware = (middleware) => {
    return applyMiddleware(...middleware)
}

export default function configureStore (initialState) {
    const store = createStore(
        rootReducer,
        initialState,
        bindMiddleware([sagaMiddleware])
    )

    store.runSagaTask = () => {
        store.sagaTask = sagaMiddleware.run(rootSaga)
    }

    store.waitSagaTaskDone = async (isServer) => {
        store.dispatch(END)
        await store.sagaTask.done
        if (!isServer) {
            // Run again for client
            store.runSagaTask()
        }
    }
    
    store.runSagaTask()
    return store
}