import React, { Component } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  TextInput,
  FlatList,
  TouchableOpacity,
} from 'react-native';
import { Icon, Divider } from 'react-native-elements';

import searchStyles from '../styles/search';
import CustomButton from '../components/CustomButton';
import VerticalSpacer from '../components/VerticalSpacer';
import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  ...searchStyles(),
});

const items = [
  { name: 'MFF-FL-323 Sibipiruna', key:'1'},
  { name: 'MFF-FL-324 Sibipiruna', key:'2'},
  { name: 'MFF-FL-325 Sibipiruna', key:'3'},
  { name: 'MFF-FL-326 Sibipiruna', key:'4'},
  { name: 'MFF-FL-327 Sibipiruna', key:'5'},
  { name: 'MFF-FL-328 Sibipiruna', key:'6'},
  { name: 'MFF-FL-329 Sibipiruna', key:'7'},
]
const footnoteFirst = 'Copyright © 2009 LABES';
const footnoteSecond = 'Laboratório de Engenharia de Software - ICMC-USP';

const Search = () => {
  return (
    <View style={styles.container}>
    <View style={styles.list}>
    <FlatList
    data={items}
    renderItem={({ item }) => {
      return (
        <TouchableOpacity>
        <Text style={styles.item}>{item.name}</Text>
        </TouchableOpacity>
      )
    }}
    />
    </View>
    <View
    style={styles.footerBar}
    />
    <Text style={styles.footnoteText}>{footnoteFirst}{'\n'}{footnoteSecond}</Text>
    </View>
  );
};

Search.navigationOptions = () => ({
  headerTitle: 'Busca',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default Search;
