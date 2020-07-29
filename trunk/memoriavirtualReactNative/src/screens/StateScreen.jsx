import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
} from 'react-native';

import subjectsDescriptorStyles from '../styles/subjectsDescriptor';
import Title from '../components/Title';
import CustomTextInfo from '../components/CustomTextInfo';

const styles = StyleSheet.create({
  ...subjectsDescriptorStyles(),
});

const StateScreen = () => (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle="Estado de preservação"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Estado de conservação"
            infoValue="-"
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={styles.lastFormTitleText}
              >Notas do estado de conservação</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <TextInput
                value="-"
              />
            </View>

          </View>

      </View>

      <Footnote />
    </View>
);

StateScreen.navigationOptions = () => ({
  headerTitle: 'Estado',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default StateScreen;
