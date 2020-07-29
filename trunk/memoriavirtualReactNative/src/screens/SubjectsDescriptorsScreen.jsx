import React, { Component, useState } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  TextInput,
} from 'react-native';

import subjectsDescriptorStyles from '../styles/subjectsDescriptor';
import CustomTextInfo from '../components/CustomTextInfo';

const styles = StyleSheet.create({
  ...subjectsDescriptorStyles(),
});


const footnoteFirst = 'Copyright © 2009 LABES';
const footnoteSecond = 'Laboratório de Engenharia de Software - ICMC-USP';

const SubjectsDescriptorsScreen = () => {

  return (
    <View style={styles.container}>
      <View style = {styles.title}>
        <Text style={{fontSize:26}}>Sibipiruna4</Text>
      </View>
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle="Assuntos"
            infoValue="-"
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={{fontSize:18}}
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

    <Text style={styles.footnoteText}>{footnoteFirst}{'\n'}{footnoteSecond}</Text>
    </View>
  );
};

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
