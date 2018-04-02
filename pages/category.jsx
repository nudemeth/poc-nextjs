import React from 'react';
import fetch from 'isomorphic-unfetch';
import { bindActionCreators } from 'redux';
import withRedux from 'next-redux-wrapper';
import { connect } from 'react-redux';
import Container from '../components/layout/Container';
import { loadCategories } from '../actions';
import { withReduxSaga } from '../store';
import CategoryList from '../components/page/category/CategoryList';

class Category extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadCategories());
    }

    componentDidMount() {
        this.props.dispatch(loadCategories());
    }

    render() {
        return (
            <Container title='Category'>
                <CategoryList/>
            </Container>
        );
    }
}

export default withReduxSaga()(Category);
export { Category };