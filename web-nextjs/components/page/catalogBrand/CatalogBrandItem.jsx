import React from 'react';
import PropTypes from 'prop-types';
import GridListTile from '@material-ui/core/GridListTile';
import Icon from '@material-ui/core/Icon';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { updateSelectedCatalogBrand } from '../../../actions/catalogBrand.actions';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';

const defaultColor = theme => theme.palette.grey[500];
const selectedColor = theme => theme.palette.secondary.main;
const catalogBrandStyle = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: '1px',
    borderStyle: 'solid',
    backgroundColor: '#fff'
}

const styles = theme => ({
    iconButtonRoot: {
        width: '100%',
        height: '100%',
        '&:hover': {
            backgroundColor: '#fff'
        }
    },
    iconButtonLabel: {
        width: '100%',
        height: 80,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-evenly'
    },
    icon: {
        color: defaultColor(theme)
    },
    selectedIcon: {
        color: selectedColor(theme)
    },
    catalogBrandTile: {
        ...catalogBrandStyle,
        borderColor: defaultColor(theme)
    },
    selectedCatalogBrandTile: {
        ...catalogBrandStyle,
        borderColor: selectedColor(theme)
    },
    catalogBrandRoot: {
        height: 184,
        padding: 2,
        [theme.breakpoints.up('lg')]: {
            width: '33.3333%'
        },
        [theme.breakpoints.down('md')]: {
            width: '50%'
        },
        [theme.breakpoints.down('xs')]: {
            width: '100%'
        }
    }
});

class CatalogBrandItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isSelected: this.props.catalogBrand.isSelected
        }
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        catalogBrand: PropTypes.object.isRequired
    }

    handleCatalogBrandToggle = () => {
        this.props.dispatch(updateSelectedCatalogBrand(this.props.catalogBrand, !this.state.isSelected));
        this.setState({ isSelected: !this.state.isSelected });
    }

    render() {
        const { classes, catalogBrand } = this.props;
        const iconClass = this.state.isSelected ? classes.selectedIcon : classes.icon;
        const catalogBrandTileClass = this.state.isSelected ? classes.selectedCatalogBrandTile : classes.catalogBrandTile;
        return (
            <GridListTile classes={{root: classes.catalogBrandRoot, tile: catalogBrandTileClass}}>
                <IconButton disableRipple classes={{root: classes.iconButtonRoot, label: classes.iconButtonLabel}} onClick={this.handleCatalogBrandToggle}>
                    <Icon className={iconClass} >{catalogBrand.icon}</Icon>
                    <Typography component="span">{catalogBrand.brand}</Typography>
                </IconButton>
            </GridListTile>
        );
    }
}

export default connect()(withStyles(styles, { withTheme: true })(CatalogBrandItem));
export { CatalogBrandItem };