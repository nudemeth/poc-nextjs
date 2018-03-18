import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import ProductList from '../components/page/products/ProductList';
import Criteria from '../components/page/products/Criteria';
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
        const headerText = <span>{this.props.text}</span>
        return (
            <Container title='Products' header={headerText}>
                <Criteria/>
                <ProductList/>
            </Container>
        );
    }
}

export default withReduxSaga()(Products);
export { Products };