import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
} from 'react-native';

import subjectsDescriptorStyles from '../styles/subjectsDescriptor';
import Title from '../components/Title';
import Footnote from '../components/Footnote';
import CustomTextInfo from '../components/CustomTextInfo';

const styles = StyleSheet.create({
  ...subjectsDescriptorStyles(),
});

const SubjectsDescriptorsScreen = () => (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle="Assuntos"
            infoValue="-"
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={styles.lastFormTitleText}
              >Descritores</Text>
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

SubjectsDescriptorsScreen.navigationOptions = () => ({
  headerTitle: 'Assuntos e descritores',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default SubjectsDescriptorsScreen;
