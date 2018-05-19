import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import ProductList from '../components/page/index/ProductList';
import HeaderContent from '../components/page/index/HeaderContent';
import { loadProducts } from '../actions/product.actions';
import { withReduxSaga } from '../store/store';

class Index extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadProducts());
        return { text: "Index Page" };
    }

    componentDidMount() {
        this.props.dispatch(loadProducts());
    }

    render() {
        return (
            <Container title='Index' header={<HeaderContent/>}>
                <ProductList/>
            </Container>
        );
    }
}

export default withReduxSaga()(Index);
export { Index };