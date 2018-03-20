import React from 'react';
import fetch from 'isomorphic-unfetch';
import { bindActionCreators } from 'redux';
import withRedux from 'next-redux-wrapper';
import { connect } from 'react-redux';
import Container from '../components/layout/Container';
import { initGreeting, updateGreeting } from '../actions';
import { withReduxSaga } from '../store';

class About extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(initGreeting('This is from server'));
        return {};
    }

    componentDidMount() {
        this.props.dispatch(updateGreeting());
    }

    render() {
        return (
            <Container title='About'>
                <h1>This is About Page: {this.props.greeting}</h1>
            </Container>
        );
    }
}

const mapStateToProps = ({ greeting }) => ({ greeting });

export default withReduxSaga(mapStateToProps)(About);
export { About };