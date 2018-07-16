import React from 'react';
import Container from '../components/layout/Container';
import { loadCatalogBrands } from '../actions/catalogBrand.actions';
import { withReduxSaga } from '../store/store';
import CatalogBrandList from '../components/page/catalogBrand/CatalogBrandList';

class CatalogBrand extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadCatalogBrands());
    }

    componentDidMount() {
        this.props.dispatch(loadCatalogBrands());
    }

    render() {
        return (
            <Container title='Catalog Brand'>
                <CatalogBrandList/>
            </Container>
        );
    }
}

export default withReduxSaga()(CatalogBrand);
export { CatalogBrand };