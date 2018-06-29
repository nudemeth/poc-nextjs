import React from 'react';
import PropTypes from 'prop-types';
import GridList from '@material-ui/core/GridList';
import Hidden from '@material-ui/core/Hidden';
import uuidv4 from 'uuid/v4';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import ProductItem from './ProductItem';

const styles = theme => ({
    gridList: {
        width: '100%',
        [theme.breakpoints.up('lg')]: {
            paddingLeft: '15%',
            paddingRight: '15%'
        }
    }
});

class ProductList extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        products: PropTypes.arrayOf(PropTypes.object).isRequired
    }

    render() {
        const { classes, products } = this.props;
        const items = products.map((product, index) => <ProductItem key={product.id} product={product} />);
        return (
            <React.Fragment>
                <Hidden mdDown key={uuidv4()}>
                    <GridList cellHeight={180} cols={3} spacing={24} className={classes.gridList}>
                        {items}
                    </GridList>
                </Hidden>
                <Hidden xsDown lgUp key={uuidv4()}>
                    <GridList cellHeight={180} cols={2} spacing={16} className={classes.gridList}>
                        {items}
                    </GridList>
                </Hidden>
                <Hidden smUp key={uuidv4()}>
                    <GridList cellHeight={180} cols={1} className={classes.gridList}>
                        {items}
                    </GridList>
                </Hidden>
            </React.Fragment>
        );
    }
}

const mapStateToProps = ({ productReducer: { products, error }}) => ({ products, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(ProductList));
export { ProductList };