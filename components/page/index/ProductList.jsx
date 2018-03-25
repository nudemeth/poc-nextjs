import React from 'react';
import PropTypes from 'prop-types';
import GridList, { GridListTile, GridListTileBar } from 'material-ui/GridList';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Hidden from 'material-ui/Hidden';
import uuidv4 from 'uuid/v4';
import { withStyles } from 'material-ui/styles';
import { connect } from 'react-redux';
import { loadProducts } from '../../../actions';

const styles = theme => ({
    listContainer: {
        listStyle: 'none',
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, 300px)',
        gridGap: '2rem',
        justifyContent: 'space-around',
        padding: 0
    },
    gridList: {
        width: '100%',
        height: '100%',
        [theme.breakpoints.up('lg')]: {
            paddingLeft: '15%',
            paddingRight: '15%'
        }
    },
    button: {
        color: '#fff'
    }
});

const createProductItem = (product, classes) => {
    return (
        <GridListTile key={product.id}>
            <img src={product.imageUrl} alt={product.imageAlt} />
            <GridListTileBar
                title={product.name}
                actionIcon={
                    <IconButton className={classes.button} aria-label="Add to shopping cart">
                        <Icon>add_shopping_cart</Icon>
                    </IconButton>
                }
            />
        </GridListTile>
    );
}

class ProductList extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes, products } = this.props;
        const items = products.map((product, index) => createProductItem(product, classes));
        return [
            <Hidden mdDown key={uuidv4()}>
                <GridList cellHeight={180} cols={3} spacing={24} className={classes.gridList}>
                    {items}
                </GridList>
            </Hidden>
            ,<Hidden xsDown lgUp key={uuidv4()}>
                <GridList cellHeight={180} cols={2} spacing={16} className={classes.gridList}>
                    {items}
                </GridList>
            </Hidden>
            ,<Hidden smUp key={uuidv4()}>
                <GridList cellHeight={180} cols={1} className={classes.gridList}>
                    {items}
                </GridList>
            </Hidden>
        ];
    }
}

const mapStateToProps = ({ products, error }) => ({ products, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(ProductList));