import React from 'react';
import PropTypes from 'prop-types';
import GridListTile from '@material-ui/core/GridListTile';
import Icon from '@material-ui/core/Icon';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { updateSelectedCatalogType } from '../../../actions/catalogType.actions';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';

const defaultColor = theme => theme.palette.grey[500];
const selectedColor = theme => theme.palette.secondary.main;
const catalogTypeStyle = {
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
    catalogTypeTile: {
        ...catalogTypeStyle,
        borderColor: defaultColor(theme)
    },
    selectedCatalogTypeTile: {
        ...catalogTypeStyle,
        borderColor: selectedColor(theme)
    },
    catalogTypeRoot: {
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

class CatalogTypeItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isSelected: this.props.catalogType.isSelected
        }
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        catalogType: PropTypes.object.isRequired
    }

    handleCatalogTypeToggle = () => {
        this.props.dispatch(updateSelectedCatalogType(this.props.catalogType, !this.state.isSelected));
        this.setState({ isSelected: !this.state.isSelected });
    }

    render() {
        const { classes, catalogType } = this.props;
        const iconClass = this.state.isSelected ? classes.selectedIcon : classes.icon;
        const catalogTypeTileClass = this.state.isSelected ? classes.selectedCatalogTypeTile : classes.catalogTypeTile;
        return (
            <GridListTile classes={{root: classes.catalogTypeRoot, tile: catalogTypeTileClass}}>
                <IconButton disableRipple classes={{root: classes.iconButtonRoot, label: classes.iconButtonLabel}} onClick={this.handleCatalogTypeToggle}>
                    <Icon className={iconClass} >{catalogType.image}</Icon>
                    <Typography component="span">{catalogType.name}</Typography>
                </IconButton>
            </GridListTile>
        );
    }
}

export default connect()(withStyles(styles, { withTheme: true })(CatalogTypeItem));
export { CatalogTypeItem };