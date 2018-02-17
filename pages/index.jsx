import React from 'react';
import fetch from 'isomorphic-unfetch';
import { bindActionCreators } from 'redux';
import { initStore, initGreeting, updateGreeting } from '../store/store';
import withRedux from 'next-redux-wrapper';
import { connect } from 'react-redux';
import Container from '../components/layout/Container';

class Index extends React.Component {
    constructor(props) {
        super(props);
    }

    static getInitialProps({ store }) {
        /*const res = await fetch('http://localhost:5000/api/v1/home');
        const data = await res.json();*/
        store.dispatch(initGreeting('This is from server'));

        //return { greeting: data.greeting };
        //return {};
    }

    componentDidMount() {
        this.timer = this.props.updateGreeting();
    }

    componentWillUnmount() {
        clearTimeout(this.timer);
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

const mapDispatchToProps = (dispatch) => {
    return {
        updateGreeting: bindActionCreators(updateGreeting, dispatch)
    };
}

export default withRedux(initStore, mapStateToProps, mapDispatchToProps)(Index);