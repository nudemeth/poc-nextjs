import React from 'react';
import Header from './Header.jsx'
import SideBar from './SideBar.jsx'
import uuidv4 from 'uuid/v4';

class Container extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return [
            <Header key={uuidv4()} />,
            <SideBar key={uuidv4()} />,
            this.props.children
        ];
    }
}

export default Container;