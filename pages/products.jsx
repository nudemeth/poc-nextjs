import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import ProductList from '../components/page/products/ProductList';
import HeaderContent from '../components/page/products/HeaderContent';
import { loadProducts } from '../actions';
import { withReduxSaga } from '../store';

class Products extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadProducts());
        return { text: "Products Page" };
    }

    componentDidMount() {
        this.props.dispatch(loadProducts());
    }

    render() {
        return (
            <Container title='Products' header={<HeaderContent/>}>
                <ProductList/>
            </Container>
        );
    }
}

export default withReduxSaga()(Products);
export { Products };