import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from 'react-native';

import searchStyles from '../styles/search';

const styles = StyleSheet.create({
  ...searchStyles(),
});

const items = [
  { name: 'MFF-FL-323 Sibipiruna', key: '1' },
  { name: 'MFF-FL-324 Sibipiruna', key: '2' },
  { name: 'MFF-FL-325 Sibipiruna', key: '3' },
  { name: 'MFF-FL-326 Sibipiruna', key: '4' },
  { name: 'MFF-FL-327 Sibipiruna', key: '5' },
  { name: 'MFF-FL-328 Sibipiruna', key: '6' },
  { name: 'MFF-FL-329 Sibipiruna', key: '7' },
  { name: 'MFF-FL-329 Sibipiruna', key: '8' },
  { name: 'MFF-FL-329 Sibipiruna', key: '9' },
  { name: 'MFF-FL-329 Sibipiruna', key: '10' },
  { name: 'MFF-FL-329 Sibipiruna', key: '11' },
];

const SearchScreen = ({ navigation }) => (
    <View style={styles.container}>
      <View style={styles.list}>
        <FlatList
          data={items}
          renderItem={({ item }) => (
            <TouchableOpacity onPress={() => navigation.navigate('Results')} >
              <Text style={styles.item}>{item.name}</Text>
            </TouchableOpacity>
          )}
        />
      </View>
    {/* <View
    style={styles.footerBar}
    />
    <Text style={styles.footnoteText}>{footnoteFirst}{'\n'}{footnoteSecond}</Text> */}
    </View>
);

SearchScreen.navigationOptions = () => ({
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

export default SearchScreen;
