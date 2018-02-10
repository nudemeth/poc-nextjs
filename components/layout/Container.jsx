import React from 'react';
import Head from 'next/head';
import Header from './Header.jsx'
import SideBar from './SideBar.jsx'
import uuidv4 from 'uuid/v4';

class Container extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return [
            <Head>
                <title>{this.props.title}</title>
                <meta name='author' content='nudemeth' />
                <meta name='viewport' content='initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width' />
                <link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500' rel='stylesheet' type='text/css' />
                <link href='https://fonts.googleapis.com/icon?family=Material+Icons' rel='stylesheet' type='text/css' />
            </Head>,
            <Header key={uuidv4()} />,
            <SideBar key={uuidv4()} />,
            this.props.children
        ];
    }
}

export default Container;