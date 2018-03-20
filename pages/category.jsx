import React from 'react';
import fetch from 'isomorphic-unfetch';
import { bindActionCreators } from 'redux';
import withRedux from 'next-redux-wrapper';
import { connect } from 'react-redux';
import Container from '../components/layout/Container';
import { initGreeting, updateGreeting } from '../actions';
import { withReduxSaga } from '../store';

class Category extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        
    }

    componentDidMount() {
        
    }

    render() {
        return (
            <Container title='Category'>
                <h1>This is Category Page</h1>
            </Container>
        );
    }
}

export default withReduxSaga()(Category);
export { Category };