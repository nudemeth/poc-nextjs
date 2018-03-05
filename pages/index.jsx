import React from 'react';
import fetch from 'isomorphic-unfetch';
import { bindActionCreators } from 'redux';
import withRedux from 'next-redux-wrapper';
import { connect } from 'react-redux';
import Container from '../components/layout/Container';
import { initGreeting, updateGreeting } from '../actions';
import { withReduxSaga } from '../store';

class Index extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        /*const res = await fetch('http://localhost:5000/api/v1/home');
        const data = await res.json();*/
        store.dispatch(initGreeting('This is from server'));

        //return { greeting: data.greeting };
        return {};
    }

    componentDidMount() {
        this.props.dispatch(updateGreeting());
    }

    render() {
        return (
            <Container title='Index'>
                <h1>This is Index Page: {this.props.greeting}</h1>
            </Container>
        );
    }
}

const mapStateToProps = ({ greeting }) => ({ greeting });

export default withReduxSaga(mapStateToProps)(Index);
export { Index };