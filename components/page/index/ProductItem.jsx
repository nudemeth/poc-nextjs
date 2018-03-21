import React from 'react';
import PropTypes from 'prop-types';
import Link from 'next/link';
import { GridListTile, GridListTileBar } from 'material-ui/GridList';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Typography from 'material-ui/Typography';
import { withStyles } from 'material-ui/styles';

const styles = theme => ({
});

class ProductItem extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes, product } = this.props;
        return (
            <GridListTile>
                <img src={product.imageUrl} alt={product.imageAlt} />
                <GridListTileBar
                    title={product.name}
                    actionIcon={
                        <IconButton color="default" aria-label="Add to shopping cart">
                            <Icon>add_shopping_cart</Icon>
                        </IconButton>
                    }
                />
            </GridListTile>
        );
    }
}

export default withStyles(styles, { withTheme: true })(ProductItem);